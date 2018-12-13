import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'varianttrim' })
export class VariantTrimPipe implements PipeTransform {
    transform(value: any) {
        if (!value) {
            return '';
        }
        return value.replace(/\s/g, '');
    }
}
