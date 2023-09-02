import { IsString, IsArray } from 'class-validator';

export class State {
    @IsString()
    name?: string;

    @IsArray()
    cities?: string[];
}