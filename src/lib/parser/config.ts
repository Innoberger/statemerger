import { Type } from 'class-transformer';
import { IsArray } from 'class-validator';
import { State } from '$lib/parser/state'

export class Config {
    @IsArray()
    @Type(() => State)
    states?: State[];
}
