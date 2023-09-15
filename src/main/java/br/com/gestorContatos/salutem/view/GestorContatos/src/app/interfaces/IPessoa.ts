import { IContato } from "./IContato";

export interface IPessoa{
    id: string,
    cpf: string,
    nome: string,
    nomeMeio: string,
    sobrenome: string,
    dataNascimento: string,
    contatos: IContato[]
}