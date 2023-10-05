import { IPessoa } from "./IPessoa";

export interface IContato{
    id: string,
    cpf: string,
    nome: string,
    nomeMeio?: string,
    sobrenome: string,
    telefone: string,
    email:string,
}