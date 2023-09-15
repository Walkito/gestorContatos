import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PessoasComponent } from './pages/pessoas/pessoas.component';
import { ContatosComponent } from './pages/contatos/contatos.component';
import { PessoaComponent } from './pages/pessoas/pessoa/pessoa.component';
import { ContatoComponent } from './pages/contatos/contato/contato.component';

const routes: Routes = [
  {path: "", component: HomeComponent},
  {path: "pessoas", component: PessoasComponent},
  {path: "pessoas/pessoa/:id", component: PessoaComponent},
  {path: "contatos", component: ContatosComponent},
  {path: "contatos/contato/:id", component: ContatoComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
