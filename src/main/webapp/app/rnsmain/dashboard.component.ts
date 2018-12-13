import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { RnsCatgMaster, RnsCatgMasterService } from '../entities/rns-catg-master';
import { Account, BaseEntityVndrPrice, Principal } from '../core';
import { JhiAlertService } from 'ng-jhipster';
import { Dashboard } from './dashboard.model';
import { RnsQuotationService } from '../entities/rns-quotation';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { RnsQuotationVendors } from '../entities/rns-quotation-vendors';
import { RnsQuotationVariant } from '../entities/rns-quotation-variant';

import * as $ from 'jquery';
import * as d3 from 'd3';
import * as d3Scale from 'd3-scale';
import * as d3Shape from 'd3-shape';
import * as d3Array from 'd3-array';
import * as d3Axis from 'd3-axis';
import { DatePipe } from '@angular/common';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['dashboard.css']
})
export class DashboardComponent implements OnInit {
    account: Account;
    myForm: FormGroup;
    rnsCatgMasters: RnsCatgMaster[];
    dashBoard: Dashboard;
    searchCatgCode: number;

    svg: any;
    margin = 50;
    g: any;
    width: number;
    height: number;
    line;

    constructor(
        private principal: Principal,
        private fb: FormBuilder,
        private jhiAlertService: JhiAlertService,
        private rnsCatgMasterService: RnsCatgMasterService,
        private datePipe: DatePipe,
        private rnsQuotationService: RnsQuotationService
    ) {}

    public ngOnInit() {
        const date = new Date();
        this.rnsCatgMasters = [];
        if (date.getMonth() + 1 < 10) {
            this.myForm = this.fb.group({
                fecha: { year: date.getFullYear(), month: '0' + (date.getMonth() + 1) }
            });
        } else {
            this.myForm = this.fb.group({
                fecha: { year: date.getFullYear(), month: date.getMonth() + 1 }
            });
        }
        this.principal.identity().then(account => {
            this.account = account;
            if (this.account !== null && this.account.authorities !== null && this.account.authorities.indexOf('ROLE_VENDOR') !== -1) {
                this.load();
            } else {
                this.rnsCatgMasterService.querylogin().subscribe(
                    (res: HttpResponse<RnsCatgMaster[]>) => {
                        this.rnsCatgMasters = res.body;
                        this.searchCatgCode = this.rnsCatgMasters[0].id;
                        this.load();
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            }
        });
    }

    public load() {
        const yearstr = Number(this.myForm.controls['fecha'].value['year']);
        const month = Number(this.myForm.controls['fecha'].value['month']);
        let monthstr = month + '';
        if (month < 10) {
            monthstr = '0' + month;
        }
        this.dashBoard = {
            id: null,
            monthYear: monthstr + '-' + yearstr,
            catgCode: this.searchCatgCode
        };
        this.rnsQuotationService.dashboard(this.dashBoard).subscribe((dashboard: HttpResponse<Dashboard>) => {
            this.dashBoard = dashboard.body;
            // Project Chart
            const data = [dashboard.body.totalProject];
            this.testPie('projectChart', data);

            // RFQ Chart
            const dataRfq = [dashboard.body.openRfq, dashboard.body.closedRfq];
            this.testPie('rfqChart', dataRfq);

            // Bidding Chart
            const dataRfb = [dashboard.body.openRfb, dashboard.body.closedRfb];
            this.testPie('rfbChart', dataRfb);

            const ids = 'biddingChart';
            if (this.dashBoard.pricesList) {
                const chartDataTemp: RnsQuotationVendors[] = [];
                let incr = 0;
                const set = new Set();
                const map = new Map();
                this.dashBoard.pricesList.forEach(val => {
                    set.add(val.vendorCode);
                    map.set(val.vendorCode, val.vendorName);
                });
                set.forEach(vCode => {
                    incr++;
                    const rnsQuotationVendors: RnsQuotationVendors = new RnsQuotationVendors();
                    rnsQuotationVendors.vendorCode = map.get(vCode);
                    const revisionListTemp: BaseEntityVndrPrice[] = [];
                    this.dashBoard.pricesList.forEach(val => {
                        if (vCode === val.vendorCode) {
                            val.vendorCode = val.vendorName;
                            val.date = new Date(this.datePipe.transform(val.date, 'yyyy-MM-dd HH:mm:ss'));
                            val.dateformated = this.dateformater(val.date);
                            revisionListTemp.push(val);
                        }
                    });
                    if (revisionListTemp.length > 0) {
                        rnsQuotationVendors.revisionList = revisionListTemp;
                        chartDataTemp.push(rnsQuotationVendors);
                    }
                });
                if (chartDataTemp.length > 0 && set.size === incr) {
                    this.renderChart(ids, chartDataTemp, this.dashBoard.rnsQuotationVariant);
                } else {
                    d3.select('#' + ids + ' > *').remove();

                    const div_width = $('#' + ids).width();
                    const div_height = $('#' + ids).height();

                    const svg = d3
                        .select('#' + ids)
                        .append('svg')
                        .attr('width', div_width)
                        .attr('height', div_height)
                        .attr('preserveAspectRatio', 'xMidYMid')
                        .append('g')
                        .attr('transform', 'translate(' + (div_width / 2 - 50) + ',' + (div_height / 2 - 50) + ')');

                    svg
                        .append('text')
                        .text('No Data')
                        .style('font-size', '30px');
                }
            } else {
                d3.select('#' + ids + ' > *').remove();

                const div_width = $('#' + ids).width();
                const div_height = $('#' + ids).height();

                const svg = d3
                    .select('#' + ids)
                    .append('svg')
                    .attr('width', div_width)
                    .attr('height', div_height)
                    .attr('preserveAspectRatio', 'xMidYMid')
                    .append('g')
                    .attr('transform', 'translate(' + (div_width / 2 - 50) + ',' + (div_height / 2 - 50) + ')');

                svg
                    .append('text')
                    .text('No Data')
                    .style('font-size', '30px');
            }
        });
    }

    public reloadLot() {
        if (typeof this.dashBoard.selectedBid !== 'undefined') {
            this.rnsQuotationService.dashboardLot(this.dashBoard).subscribe(dashBoard => {
                this.dashBoard = dashBoard.body;
                if (this.dashBoard.pricesList) {
                    const ids = 'biddingChart';
                    const chartDataTemp: RnsQuotationVendors[] = [];
                    let incr = 0;
                    const set = new Set();
                    const map = new Map();
                    this.dashBoard.pricesList.forEach(val => {
                        set.add(val.vendorCode);
                        map.set(val.vendorCode, val.vendorName);
                    });
                    set.forEach(vCode => {
                        incr++;
                        const rnsQuotationVendors: RnsQuotationVendors = new RnsQuotationVendors();
                        rnsQuotationVendors.vendorCode = map.get(vCode);
                        const revisionListTemp: BaseEntityVndrPrice[] = [];
                        this.dashBoard.pricesList.forEach(val => {
                            if (vCode === val.vendorCode) {
                                val.vendorCode = val.vendorName;
                                val.date = new Date(this.datePipe.transform(val.date, 'yyyy-MM-dd HH:mm:ss'));
                                val.dateformated = this.dateformater(val.date);
                                revisionListTemp.push(val);
                            }
                        });
                        if (revisionListTemp.length > 0) {
                            rnsQuotationVendors.revisionList = revisionListTemp;
                            chartDataTemp.push(rnsQuotationVendors);
                        }
                    });
                    if (chartDataTemp.length > 0 && set.size === incr) {
                        this.renderChart(ids, chartDataTemp, this.dashBoard.rnsQuotationVariant);
                    } else {
                        d3.select('#' + ids + ' > *').remove();
                        const div_width = $('#' + ids).width();
                        const div_height = $('#' + ids).height();

                        const svg = d3
                            .select('#' + ids)
                            .append('svg')
                            .attr('width', div_width)
                            .attr('height', div_height)
                            .attr('preserveAspectRatio', 'xMidYMid')
                            .append('g')
                            .attr('transform', 'translate(' + (div_width / 2 - 50) + ',' + (div_height / 2 - 50) + ')');

                        svg
                            .append('text')
                            .text('No Data')
                            .style('font-size', '30px');
                    }
                }
            });
        }
    }

    public reloadChart() {
        if (typeof this.dashBoard.selectedBid !== 'undefined' && typeof this.dashBoard.selectedLot !== 'undefined') {
            this.rnsQuotationService.dashboardChart(this.dashBoard).subscribe((dashBoard: HttpResponse<Dashboard>) => {
                this.dashBoard = dashBoard.body;
                if (this.dashBoard.pricesList) {
                    const ids = 'biddingChart';
                    const chartDataTemp: RnsQuotationVendors[] = [];
                    let incr = 0;
                    const set = new Set();
                    const map = new Map();
                    this.dashBoard.pricesList.forEach(val => {
                        set.add(val.vendorCode);
                        map.set(val.vendorCode, val.vendorName);
                    });
                    set.forEach(vCode => {
                        incr++;
                        const rnsQuotationVendors: RnsQuotationVendors = new RnsQuotationVendors();
                        rnsQuotationVendors.vendorCode = map.get(vCode);
                        const revisionListTemp: BaseEntityVndrPrice[] = [];
                        this.dashBoard.pricesList.forEach(val => {
                            if (vCode === val.vendorCode) {
                                val.vendorCode = val.vendorName;
                                val.date = new Date(this.datePipe.transform(val.date, 'yyyy-MM-dd HH:mm:ss'));
                                val.dateformated = this.dateformater(val.date);
                                revisionListTemp.push(val);
                            }
                        });
                        if (revisionListTemp.length > 0) {
                            rnsQuotationVendors.revisionList = revisionListTemp;
                            chartDataTemp.push(rnsQuotationVendors);
                        }
                    });
                    if (chartDataTemp.length > 0 && set.size === incr) {
                        this.renderChart(ids, chartDataTemp, this.dashBoard.rnsQuotationVariant);
                    } else {
                        d3.select('#' + ids + ' > *').remove();
                        const div_width = $('#' + ids).width();
                        const div_height = $('#' + ids).height();

                        const svg = d3
                            .select('#' + ids)
                            .append('svg')
                            .attr('width', div_width)
                            .attr('height', div_height)
                            .attr('preserveAspectRatio', 'xMidYMid')
                            .append('g')
                            .attr('transform', 'translate(' + (div_width / 2 - 50) + ',' + (div_height / 2 - 50) + ')');

                        svg
                            .append('text')
                            .text('No Data')
                            .style('font-size', '30px');
                    }
                }
            });
        }
    }

    private testPie(id, data) {
        // const data = [15, 50];

        const width = 300,
            height = 190,
            radius = Math.min(width, height) / 2;

        const color = d3
            .scaleOrdinal()
            .range([
                'rgba(0, 176, 88, 0.5)',
                'rgba(255, 49, 32, 0.5)',
                'rgba(0, 102, 204, 0.5)',
                'rgba(153, 0, 204, 0.5)',
                'rgba(204, 102, 0, 0.5)',
                'rgba(191, 0, 255, 0.5)',
                'rgba(255, 128, 0, 0.5)'
            ]);

        const arc = d3
            .arc()
            .outerRadius(radius - 10)
            .innerRadius(0);

        const labelArc = d3
            .arc()
            .outerRadius(radius - 40)
            .innerRadius(radius - 40);

        const pie = d3
            .pie()
            .sort(null)
            .value(function(d) {
                return d;
            });

        $('#' + id + ' svg').remove();

        const svg = d3
            .select('#' + id)
            .append('svg')
            .attr('width', width)
            .attr('height', height)
            .attr('id', 'testPie')
            .append('g')
            .attr('transform', 'translate(' + width / 2 + ',' + height / 2 + ')');

        const pieChart = svg
            .selectAll('.arc')
            .data(pie(data))
            .enter()
            .append('g')
            .attr('class', 'arc');
        // Pie pieces
        pieChart
            .append('path')
            .attr('d', arc)
            .attr('opacity', '1.0')
            .on('mouseenter', function(d) {
                const arcOver = d3
                    .arc()
                    .outerRadius(radius)
                    .innerRadius(0)
                    .startAngle(d.startAngle + 0.01)
                    .endAngle(d.endAngle - 0.01);
                // const transformText = this.getTranslate(d.startAngle + 0.01, d.endAngle - 0.01, 20);

                const startAngle = d.startAngle + 0.01;
                const endAngle = d.endAngle - 0.01;
                const distance = 20;

                let xTranslate, yTranslate;
                let startQ = 4;
                switch (true) {
                    case startAngle < Math.PI * 0.5:
                        startQ = 1;
                        break;
                    case startAngle >= Math.PI * 1.5:
                        startQ = 2;
                        break;
                    case Math.PI < startAngle && startAngle <= Math.PI * 1.5:
                        startQ = 3;
                        break;
                    default:
                        startQ = 4;
                        break;
                }
                // const startQ = this.getQuadrant(startAngle);

                let endQ = 4;
                switch (true) {
                    case endAngle < Math.PI * 0.5:
                        endQ = 1;
                        break;
                    case endAngle >= Math.PI * 1.5:
                        endQ = 2;
                        break;
                    case Math.PI < endAngle && endAngle <= Math.PI * 1.5:
                        endQ = 3;
                        break;
                    default:
                        endQ = 4;
                        break;
                }
                // const endQ = this.getQuadrant(endAngle);

                // Assume there are 7 possibilities since last slice always ends at Tau or 12 o'clock when doing a d.endAngle
                switch (true) {
                    case startQ === 1 && endQ === 1:
                        xTranslate = distance * 0.5;
                        yTranslate = distance * -1.5;
                        break;
                    case startQ === 1 && endQ === 4:
                        xTranslate = distance * 1.5;
                        yTranslate = distance * 0.5;
                        break;
                    case startQ === 4 && endQ === 4:
                        xTranslate = distance * 0.5;
                        yTranslate = distance * 1.5;
                        break;
                    case startQ === 4 && endQ === 3:
                        xTranslate = distance * -0.5;
                        yTranslate = distance * 1.5;
                        break;
                    case startQ === 3 && endQ === 3:
                        xTranslate = distance * -1.5;
                        yTranslate = distance * 0.5;
                        break;
                    case startQ === 3 && endQ === 2:
                        xTranslate = distance * -1.5;
                        yTranslate = distance * -0.5;
                        break;
                    case startQ === 2 && endQ === 2:
                        xTranslate = distance * -0.5;
                        yTranslate = distance * -1.5;
                        break;
                }
                if (typeof xTranslate !== 'undefined' && typeof yTranslate !== 'undefined') {
                    const transformText = 'translate(' + xTranslate + ',' + yTranslate + ')';

                    d3
                        .select(this)
                        .attr('d', arcOver)
                        .transition()
                        .duration(200)
                        .ease(d3.easeBack)
                        .attr('transform', transformText)
                        .attr('style', 'fill: rgb(102, 102, 102)');
                }
            })
            .on('mouseleave', function(d) {
                d3
                    .select(this)
                    .attr('d', arc)
                    .transition()
                    .ease(d3.easeBack)
                    .duration(600)
                    .attr('transform', 'translate(0,0)')
                    .attr('style', 'fill: ' + color(d.data));
            })
            .style('fill', function(d) {
                return color(d.data);
            });

        pieChart
            .append('text')
            .attr('transform', function(d) {
                return 'translate(' + labelArc.centroid(d) + ')';
            })
            .attr('fill', '#fff')
            .attr('dy', '.35em')
            .text(function(d) {
                return d.data;
            });
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }

    private dateformater(date: Date): string {
        const map = new Map<number, string>();
        map.set(0, 'Jan');
        map.set(1, 'Feb');
        map.set(2, 'Mar');
        map.set(3, 'Apr');
        map.set(4, 'May');
        map.set(5, 'Jun');
        map.set(6, 'Jul');
        map.set(7, 'Aug');
        map.set(8, 'Sep');
        map.set(9, 'Oct');
        map.set(10, 'Nov');
        map.set(11, 'Dec');

        let minutes: any;
        if (date.getMinutes() > 9) {
            minutes = date.getMinutes();
        } else {
            minutes = '0' + date.getMinutes();
        }

        let hours: any;
        if (date.getHours() > 9) {
            hours = date.getHours();
        } else {
            hours = '0' + date.getHours();
        }

        const dateFormat = date.getDate() + '-' + map.get(date.getMonth()) + '-' + date.getFullYear() + ' ' + hours + ':' + minutes;
        return dateFormat;
    }

    private renderChart(ids, chartDataTemp: RnsQuotationVendors[], variant: RnsQuotationVariant) {
        // var data:any = chartDataTemp.map((v) => v.revisionList.map((v) => new Date(this.datePipe.transform(v.date, 'yyyy-MM-dd HH:mm:ss')) ))[0];
        const dates = [];
        chartDataTemp.forEach(val => {
            val.revisionList.forEach(revval => {
                dates.push(new Date(this.datePipe.transform(revval.date, 'yyyy-MM-dd HH:mm:ss')));
            });
        });
        const data: any = [new Date(Math.min.apply(null, dates)), new Date(Math.max.apply(null, dates))];
        // console.log('dataaaaaaaaaaaaa',data);
        if (typeof data !== 'undefined') {
            let firstDate = data[0];
            firstDate = firstDate.getTime() - 1 * 30000;
            data[0] = new Date(firstDate);

            /*if (variant.historicalPrice) {
                const rnsQuotationVendors: RnsQuotationVendors = new RnsQuotationVendors();
                rnsQuotationVendors.vendorCode = 'Historical Price (' + variant.historicalPrice + ' ' + variant.currency + ')';
                const revisionListTemp: BaseEntityVndrPrice[] = [];

                const revisionMin: BaseEntityVndrPrice = new BaseEntityVndrPrice();
                revisionMin.vendorCode = 'Historical Price';
                revisionMin.date = data[0];

                revisionMin.dateformated = this.dateformater(data[0]);
                revisionMin.value = variant.historicalPrice;
                revisionListTemp.push(revisionMin);

                const revisionMax: BaseEntityVndrPrice = new BaseEntityVndrPrice();
                revisionMax.vendorCode = 'Historical Price';
                revisionMax.date = data[1];
                revisionMax.dateformated = this.dateformater(data[1]);
                revisionMax.value = variant.historicalPrice;
                revisionListTemp.push(revisionMax);
                rnsQuotationVendors.revisionList = revisionListTemp;

                chartDataTemp.push(rnsQuotationVendors);
            }*/
            this.initChart(ids, chartDataTemp, data, variant);
        }
    }

    private initChart(ids, chartData: RnsQuotationVendors[], data, variant: RnsQuotationVariant): void {
        // console.log('Line 1194---');
        if (chartData.length > 0 && data.length > 0) {
            // console.log('Line 1196---',ids);

            this.width = 500;
            this.height = 300;

            /* Scale */
            const x: any = d3Scale.scaleTime().range([0, this.width - this.margin]);

            const x2: any = d3Scale.scaleTime().range([0, this.width - this.margin]);

            const y: any = d3Scale.scaleLinear().range([this.height - this.margin, 0]);

            const z: any = d3Scale.scaleOrdinal(d3Scale.schemeCategory10);

            /* Remove SVG */
            d3.select('#' + ids + ' > *').remove();

            /* Add SVG */
            this.svg = d3
                .select('#' + ids)
                .attr('width', this.width + this.margin + 'px')
                .attr('height', this.height + this.margin + 'px');

            this.g = this.svg.append('g').attr('transform', 'translate(' + this.margin + ',' + this.margin + ')');

            this.line = d3Shape
                .line()
                // .curve(d3Shape.curveBasis)
                .x((d: any) => x(new Date(this.datePipe.transform(d.date, 'yyyy-MM-dd HH:mm:ss'))))
                .y((d: any) => y(d.value));

            x.domain(d3Array.extent(data, (d: Date) => d));

            y.domain([
                d3Array.min(chartData, function(c) {
                    return d3Array.min(c.revisionList, function(d) {
                        return d.value;
                    });
                }) - 2,
                d3Array.max(chartData, function(c) {
                    return d3Array.max(c.revisionList, function(d) {
                        return d.value;
                    });
                })
            ]);

            z.domain(
                chartData.map(function(c) {
                    return c.id;
                })
            );
            // console.log('Line 1190---');
            // if(refresh === false) {
            this.drawAxis(x, y, x2, variant);
            // }
            this.drawPath(chartData, x, y, z);
        }
    }

    private drawAxis(x, y, x2, variant: RnsQuotationVariant): void {
        const lineOpacity = '0.25';
        const lineOpacityHover = '0.85';
        const lineStroke = '1.5px';
        const lineStrokeHover = '2.5px';

        const xAxis = d3Axis.axisBottom(x).ticks(5);
        const yAxis = d3Axis.axisLeft(y).ticks(5);
        const x2Axis = d3Axis.axisBottom(x2).ticks(0);

        this.g
            .append('g')
            .attr('class', 'x axis')
            .attr('transform', 'translate(0, ' + (this.height - this.margin) + ')')
            .call(xAxis);

        this.g
            .append('g')
            .attr('class', 'y axis')
            .call(yAxis)
            .append('text')
            .attr('y', 15)
            .attr('transform', 'rotate(-90)')
            .attr('fill', '#000')
            .text('Rates, INR');
    }

    private drawPath(chartData: RnsQuotationVendors[], x, y, z): void {
        const lineOpacity = '0.25';
        const lineOpacityHover = '0.85';
        const otherLinesOpacityHover = '0.1';
        const lineStroke = '1.5px';
        const lineStrokeHover = '2.5px';
        const circleOpacity = '0.85';
        const circleOpacityOnLineHover = '0.25';
        const circleRadius = 3;
        const circleRadiusHover = 6;
        const duration = 250;

        let tooltip = d3
            .select('body')
            .append('div')
            .attr('class', 'tooltip')
            .style('opacity', 0);
        const city = this.g
            .selectAll('.line-group')
            .data(chartData)
            .enter()
            .append('g')
            .attr('class', 'line-group')
            .style('fill', 'white')
            .on('mouseover', function(d, i) {
                city
                    .append('text')
                    .attr('class', 'title-text')
                    .style('fill', h => {
                        if (h.vendorCode != null && h.vendorCode.startsWith('Historical Price')) {
                            return '#7C0A02';
                        } else {
                            return z(i);
                        }
                    })
                    .text(d.vendorCode)
                    .attr('text-anchor', 'middle')
                    .attr('x', 560 / 2)
                    .attr('y', -10);
            })
            .on('mouseout', function(d) {
                city.select('.title-text').remove();
            });
        // console.log('-----------------------2', chartData, city.data());
        city
            .append('path')
            .attr('class', 'line')
            .attr('d', d => this.line(d.revisionList))
            .style('stroke', (d, i) => {
                if (d.vendorCode != null && d.vendorCode.startsWith('Historical Price')) {
                    return '#7C0A02';
                } else {
                    return z(i);
                }
            })
            .style('opacity', lineOpacity)
            .on('mouseover', function(d) {
                d3.selectAll('.line').style('opacity', otherLinesOpacityHover);
                d3.selectAll('.circle').style('opacity', circleOpacityOnLineHover);
                d3
                    .select(this)
                    .style('opacity', lineOpacityHover)
                    .style('stroke-width', lineStrokeHover)
                    .style('cursor', 'pointer');
            })
            .on('mouseout', function(d) {
                d3.selectAll('.line').style('opacity', lineOpacity);
                d3.selectAll('.circle').style('opacity', circleOpacity);
                d3
                    .select(this)
                    .style('stroke-width', lineStroke)
                    .style('cursor', 'none');
            });
        // console.log('-----------------------3');

        /* Add circles in the line */
        const circleGroup = this.g
            .selectAll('circle-group')
            .data(chartData)
            .enter()
            .append('g')
            .style('fill', (d, i) => {
                if (d.vendorCode != null && d.vendorCode.startsWith('Historical Price')) {
                    return '#7C0A02';
                } else {
                    return z(i);
                }
            });
        tooltip = d3.select('body');
        circleGroup
            .selectAll('circle')
            .data(d => d.revisionList)
            .enter()
            .append('g')
            .attr('class', 'circle')
            .on('mouseover', function(d) {
                tooltip
                    .style('cursor', 'pointer')
                    .append('div')
                    .attr('class', 'tooltip')
                    .style('opacity', 0)
                    .html(
                        '<strong> Party:&nbsp;</strong>' +
                            d.vendorCode +
                            '<br>' +
                            '<strong> Rate:&nbsp;</strong>' +
                            d.value +
                            '<br>' +
                            '<strong> Time:&nbsp;</strong>' +
                            d.dateformated
                    )
                    .style('background-color', ' rgba(255,255,255,1)')
                    .style('border', '1px solid #ddd')
                    .style('box-shadow', '4px 4px 12px rgba(0,0,0,.5')
                    .style('border-radius', '15px')
                    .style('padding', '5px 8px 5px 8px')
                    .style('font-family', 'Arial')
                    .style('font-size', '13px')
                    .style('top', d3.event.pageY + 10 + 'px')
                    .style('left', d3.event.pageX + 10 + 'px')
                    .style('opacity', 1);
            })
            .on('mouseout', function(d) {
                tooltip
                    .style('cursor', 'default')
                    .selectAll('.tooltip')
                    .remove();
            })
            .append('circle')
            .attr('cx', function(d) {
                return x(d.date);
            })
            .attr('cy', function(d) {
                return y(d.value);
            })
            .attr('r', circleRadius)
            .on('mouseover', function(d) {
                d3
                    .select(this)
                    .style('cursor', 'pointer')
                    .attr('r', circleRadiusHover);
            })
            .on('mouseout', function(d) {
                d3
                    .select(this)
                    .style('cursor', 'none')
                    .attr('r', circleRadius);
            });
    }
}
