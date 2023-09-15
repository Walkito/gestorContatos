import { Component, OnInit } from '@angular/core';
import { PessoaService } from './service/pessoas.service';
import { IPessoa } from 'src/app/interfaces/IPessoa';

@Component({
  selector: 'app-pessoas',
  templateUrl: './pessoas.component.html',
  styleUrls: ['./pessoas.component.scss']
})
export class PessoasComponent implements OnInit {
  pessoas: IPessoa[] = [];
  paginas: number[] = [];
  
  id: string = "";
  nome: string = "";
  nomeMeio: string = "";
  sobrenome: string = "";
  cpf: string = "";
  dataNascimento: string = "";
  paginaAtual: number = 1;
  tamanhoPagina: string = "0";

  
  constructor(private service:PessoaService){}

  ngOnInit(): void{
    this.getPessoaFiltrada(true);
  }

  async getPessoaFiltrada(botaoFiltrar: boolean){
    botaoFiltrar === true ? this.paginaAtual = 1 : this.paginaAtual;
    const parametros = {
      id: this.id,
      cpf: this.cpf,
      nome: this.nome,
      nomeMeio: this.nomeMeio,
      sobrenome: this.sobrenome,
      dataNascimento: this.dataNascimento,
      numPagina: (this.paginaAtual-1).toString(),
      tamanhoPagina: this.tamanhoPagina.toString()
    }

    this.service.getPessoaFiltrada(parametros).subscribe({
      next: (pessoas) =>{
        if('content' in pessoas){
          this.pessoas = pessoas.content;
        }else{
          this.pessoas = pessoas;
        }
        this.formataData()
        this.paginas = this.preencherPaginas(pessoas.totalPages);
      },
      error: (error) => console.error(error),
    })
  }

  preencherPaginas(totalPaginas: number){
      return Array(totalPaginas).fill(0).map((x,i) => i + 1);
  }

  ultimaPagina(index: number): boolean{
    return index === this.paginas.length - 1;
  }

  estaNaPagina(index: number): boolean{
    return index === this.paginaAtual;
  }

  irPagina(pagina: number,event: MouseEvent){
    const paragrafos = document.querySelectorAll('.pagina-p');

    paragrafos.forEach(paragrafo => {
      paragrafo.classList.remove('pag-sublinhada');
    });

    const elemento = event.target as HTMLElement;
    elemento.classList.add('pag-sublinhada');

    this.paginaAtual = pagina;

    this.getPessoaFiltrada(false);
  }

  popularDadosFiltro(event: Event, filtro: String){
    const elemento = event.target as HTMLInputElement;
    const valor = elemento.value;

    switch(filtro){
      case 'id':
        this.id = valor;
        break;
      case 'cpf':
        this.cpf = valor;
        break;
      case 'nome':
        this.nome = valor;
        break;
      case 'nomeMeio':
        this.nomeMeio = valor;
        break;
      case 'sobrenome':
        this.sobrenome = valor;
        break;
      case 'dataNascimento':
        this.dataNascimento = valor;
        break;
      case 'tamanhoPagina':
        this.tamanhoPagina = valor;
        break;
    }
  }

  formataData(){
    this.pessoas.forEach((pessoa)=>{
      pessoa.dataNascimento = pessoa.dataNascimento.split('-').reverse().join('-');
      pessoa.dataNascimento = pessoa.dataNascimento.replaceAll('-','/');
    })
  }
}
