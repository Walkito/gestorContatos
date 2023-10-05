import { Component, OnInit } from '@angular/core';
import { IContato } from 'src/app/interfaces/IContato';
import { ContatosService } from './service/contatos.service';

@Component({
  selector: 'app-contatos',
  templateUrl: './contatos.component.html',
  styleUrls: ['./contatos.component.scss']
})
export class ContatosComponent implements OnInit{
  id: string = "0";
  nome: string = "";
  nomeMeio: string= "";
  sobrenome: string = "";
  cpf: string = "";
  telefone: string = "";
  email: string = "";
  idPessoa: string = "";
  
  contatos: IContato[] = [];

  paginas: number[] = [];
  paginaAtual: number = 0;
  tamanhoPagina: string = "";

  constructor(private service: ContatosService){}

  ngOnInit(): void {
    this.getContatoFiltrado(true);
  }

  async getContatoFiltrado(botaoFiltrar: boolean){
    botaoFiltrar === true ? this.paginaAtual = 1 : this.paginaAtual;
    this.id === "" || this.id === null || Number.parseInt(this.id) < 0 ? this.id = '0' : this.id;
    this.tamanhoPagina === "" || this.tamanhoPagina === null || Number.parseInt(this.tamanhoPagina) < 0 ? this.tamanhoPagina = '0' : this.tamanhoPagina;

    const parametros = {
      id: this.id,
      cpf: this.cpf,
      nome: this.nome,
      nomeMeio: this.nomeMeio,
      sobrenome: this.sobrenome,
      telefone: this.telefone,
      email: this.email,
      numPagina: (this.paginaAtual-1).toString(),
      tamanhoPagina: this.tamanhoPagina
    }

    this.service.getContatoFiltrado(parametros).subscribe({
      next: (contatos) =>{
        if('content' in contatos){
          this.contatos = contatos.content;
        }else{
          this.contatos = contatos;
        }
        this.paginas = this.preencherPaginas(contatos.totalPages);
        this.formataCPF();
        this.formatarTelefone();
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

    this.getContatoFiltrado(false);
  }

  formataCPF(){
    this.contatos.forEach((contato) =>{
      contato.cpf = contato.cpf.substring(0, 3) + '.' + contato.cpf.substring(3, 6) + '.' + contato.cpf.substring(6, 9) + '-' + contato.cpf.substring(9);
    })
  }

  formatarTelefone(){
    this.contatos.forEach((contato) => {
      contato.telefone = '(' + contato.telefone.substring(0,2) + ')' + ' ' + contato.telefone.substring(2,7) + '-' + contato.telefone.substring(7,11);
    })
  }
}
