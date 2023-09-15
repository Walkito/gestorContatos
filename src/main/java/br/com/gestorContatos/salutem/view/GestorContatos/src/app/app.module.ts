import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { PessoasComponent } from './pages/pessoas/pessoas.component';
import { ContatosComponent } from './pages/contatos/contatos.component';
import { PessoaComponent } from './pages/pessoas/pessoa/pessoa.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PessoasComponent,
    ContatosComponent,
    PessoaComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    CommonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
