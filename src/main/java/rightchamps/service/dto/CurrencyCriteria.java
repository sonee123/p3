package rightchamps.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Currency entity. This class is used in CurrencyResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /currencies?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CurrencyCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter currency;

    private StringFilter symbol;

    private FloatFilter exchange_rate;

    public CurrencyCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCurrency() {
        return currency;
    }

    public void setCurrency(StringFilter currency) {
        this.currency = currency;
    }

    public StringFilter getSymbol() {
        return symbol;
    }

    public void setSymbol(StringFilter symbol) {
        this.symbol = symbol;
    }

    public FloatFilter getExchange_rate() {
        return exchange_rate;
    }

    public void setExchange_rate(FloatFilter exchange_rate) {
        this.exchange_rate = exchange_rate;
    }

    @Override
    public String toString() {
        return "CurrencyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (currency != null ? "currency=" + currency + ", " : "") +
                (symbol != null ? "symbol=" + symbol + ", " : "") +
                (exchange_rate != null ? "exchange_rate=" + exchange_rate + ", " : "") +
            "}";
    }

}
