import { Transform } from 'class-transformer';
import { IsString, IsArray, IsOptional } from 'class-validator';

export class State {
    @IsString()
    name?: string;

    @IsArray()
    cities?: string[];
}