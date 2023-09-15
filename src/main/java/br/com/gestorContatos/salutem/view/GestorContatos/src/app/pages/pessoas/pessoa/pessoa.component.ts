import { Component, OnInit } from '@angular/core';
import { PessoaService } from '../pessoa/service/pessoa.service';
import { ActivatedRoute, Router } from '@angular/router';
import { IContato } from 'src/app/interfaces/IContato';

@Component({
  selector: 'app-pessoa',
  templateUrl: './pessoa.component.html',
  styleUrls: ['./pessoa.component.scss']
})
export class PessoaComponent implements OnInit{
  id: string = "";
  nome: string = "";
  nomeMeio: string= "";
  sobrenome: string = "";
  cpf: string = "";
  dataNascimento: string = "";
  contatos: IContato[] = [];

  contatosSemPessoa: IContato[] = [];

  posContatosSelecionados: number[] = [];

  constructor(private service: PessoaService, private route: ActivatedRoute, private router: Router){
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if(params['id'] === '0'){
        this.mudarBotao(params['id']);
        this.getUltimoIndice();
      } else {
        this.id = params['id'];  
        this.mudarBotao(params['id']);
        this.getPessoaById(this.id);
      }
    });
    this.getContatosSemPessoas();
  }

  async getPessoaById(id:string){
    this.service.getPessoaById(id).subscribe({
      next:(pessoa) =>{
        this.nome = pessoa.nome;
        this.nomeMeio = pessoa.nomeMeio;
        this.sobrenome = pessoa.sobrenome;
        this.cpf = pessoa.cpf;
        this.dataNascimento = pessoa.dataNascimento;
        this.contatos = pessoa.contatos;
      },
      error:(error) =>{
        console.error(error);
      }
    }
    )
  }

  async getUltimoIndice(){
    this.service.getUltimoIndice().subscribe({
      next: (pessoa) =>{
        this.id = String(Number.parseInt(pessoa.id)+1);
      },
      error: (error) =>{
        console.error(error);
      }
    })
  }

  async getContatosSemPessoas(){
    this.service.getContatosSemPessoa().subscribe({
      next: (contatos) =>{
        if('content' in contatos){
          this.contatosSemPessoa = contatos.content;
        } else {
          this.contatosSemPessoa = contatos;
        }
      },
      error: (error) =>{
        console.error(error);
      } 
    })
  }

  async postPessoa(){
    const pessoa = this.montarPessoa();
    this.service.postPessoa(pessoa).subscribe({
      error: (error) =>{
        console.error(error);
      },
      complete: () => {
        this.router.navigate(['/pessoas']);
      }
    })
  }

  async putPessoa(){
    const pessoa = this.montarPessoa();
    this.service.putPessoa(pessoa).subscribe({
      error: (error) =>{
        console.error(error);
      },
      complete: () => {
        this.router.navigate(['/pessoas']);
      }
    }
    );
  }

  async deletePessoa(){
    this.service.detePessoa(this.id).subscribe({
      error: (error) =>{
        console.error(error);
      },
      complete: () =>{
        this.router.navigate(['/pessoas']);
      }
    })
  }

  selecionaContato(evento: Event){
    const elemento = evento.target as HTMLElement;
    const lista = Array.from(document.querySelectorAll(".div-pessoa-contatos ul li"));
    const posicaoElemento = lista.indexOf(elemento);

    if (elemento.classList.contains('selecionado')) {
      elemento.classList.remove('selecionado');
      this.posContatosSelecionados.splice(this.posContatosSelecionados.indexOf(posicaoElemento),1)
    } else {
      elemento.classList.add('selecionado');
      this.posContatosSelecionados.push(posicaoElemento);
    }
  }

  adicionarContato(){
    const elemento = document.getElementById('idContatos') as HTMLSelectElement;
    const indexOption = elemento.selectedIndex;
    const contato = this.contatosSemPessoa[indexOption];

    this.contatos.push(contato);
    this.contatosSemPessoa.splice(indexOption,1);
  }

  deleteContato(){
    this.contatos.forEach((contato) =>{
      this.posContatosSelecionados.forEach((posContato) =>{
        if(this.contatos.indexOf(contato) === posContato){
          this.contatosSemPessoa.push(contato);
          this.contatos.splice(this.contatos.indexOf(contato),1);
          this.posContatosSelecionados.splice(this.posContatosSelecionados.indexOf(posContato),1);
        }
      });
    });
    this.contatosSemPessoa.sort((a,b)=>{
      return Number(a.id) - Number(b.id);
    })
  }

  mudarBotao(id:string){
    if(id === '0'){
      const botao = document.querySelector('.btn-delete') as HTMLInputElement;
      botao.style.display = "none";
      const botao2 = document.querySelector('.btn-put') as HTMLInputElement;
      botao2.style.display = "none";
    } else {
      const botao = document.querySelector('.btn-post') as HTMLInputElement;
      botao.style.display = "none";
    }
  }

  montarPessoa(){
     return {
      id: this.id,
      nome: this.nome,
      nomeMeio: this.nomeMeio,
      sobrenome: this.sobrenome,
      cpf: this.cpf,
      dataNascimento: this.dataNascimento,
      contatos: this.contatos
    }
  }
  
}
