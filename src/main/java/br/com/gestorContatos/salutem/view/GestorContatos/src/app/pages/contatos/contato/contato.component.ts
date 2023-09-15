import { Component, OnInit } from '@angular/core';
import { IContato } from 'src/app/interfaces/IContato';
import { ContatoService } from './service/contato.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-contato',
  templateUrl: './contato.component.html',
  styleUrls: ['./contato.component.scss']
})
export class ContatoComponent implements OnInit{
  id: string = "";
  nome: string = "";
  nomeMeio: string | undefined = "";
  sobrenome: string = "";
  cpf: string = "";
  telefone: string = "";
  email: string = "";
  contatos: IContato[] = [];

  constructor(private service: ContatoService, private route: ActivatedRoute, private router: Router){}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      if(params['id'] === '0'){
        this.mudarBotao(params['id']);
        this.getUltimoIndice();
      } else {
        this.id = params['id'];  
        this.mudarBotao(params['id']);
        this.getContatoById(this.id);
      }
    });
  }

  async getContatoById(id:string){
    this.service.getContatoById(id).subscribe({
      next:(contato) =>{
        this.nome = contato.nome;
        this.nomeMeio = contato.nomeMeio;
        this.sobrenome = contato.sobrenome;
        this.cpf = contato.cpf;
        this.telefone = contato.telefone;
        this.email = contato.email;
      },
      error:(error) =>{
        console.error(error);
      }
    }
    )
  }

  async getUltimoIndice(){
    this.service.getUltimoIndice().subscribe({
      next: (contato) =>{
        this.id = String(Number.parseInt(contato.id)+1);
      },
      error: (error) =>{
        console.error(error);
      }
    })
  }

  async postContato(){
    const contato ={
      id: this.id,
      nome: this.nome,
      nomeMeio: this.nomeMeio,
      sobrenome: this.sobrenome,
      cpf: this.cpf,
      telefone: this.telefone,
      email: this.email
    }

    this.service.postContato(contato).subscribe({
      error: (error) =>{
        console.error(error);
      },
      complete: () => {
        this.router.navigate(['/contatos']);
      }
    })
  }

  async putContato(){
    const contato ={
      id: this.id,
      nome: this.nome,
      nomeMeio: this.nomeMeio,
      sobrenome: this.sobrenome,
      cpf: this.cpf,
      telefone: this.telefone,
      email: this.email
    }

    this.service.putContato(contato).subscribe({
      error: (error) =>{
        console.error(error);
      },
      complete: () => {
        this.router.navigate(['/contatos'])
      }
    }
    );
  }

  async deleteContato(){
    this.service.deteContato(this.id).subscribe({
      error: (error) =>{
        console.error(error);
      },
      complete: () =>{
        this.router.navigate(['/contatos']);
      }
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
}
