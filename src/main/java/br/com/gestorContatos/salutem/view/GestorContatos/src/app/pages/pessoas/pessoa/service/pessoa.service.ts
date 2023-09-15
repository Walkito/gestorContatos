import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Observer } from 'rxjs';
import { API_BODY_PATH } from 'src/app/environments/environment';
import { IContatoArr } from 'src/app/interfaces/IContatoArr';
import { IPessoa } from 'src/app/interfaces/IPessoa';

@Injectable({
  providedIn: 'root'
})
export class PessoaService {
  private pathBase = API_BODY_PATH;
  constructor(private http: HttpClient) { }

  getPessoaById(id:string): Observable<IPessoa>{
    return this.http.get<IPessoa>(`${this.pathBase}pessoas/pessoa?id=${id}`);
  }

  getUltimoIndice(): Observable<IPessoa>{
    return this.http.get<IPessoa>(`${this.pathBase}pessoas/pessoa/ultimoId`);
  }

  getContatosSemPessoa(): Observable<IContatoArr>{
    return this.http.get<IContatoArr>(`${this.pathBase}contatos/semPessoas`);
  }

  postPessoa(pessoa: IPessoa): Observable<IPessoa>{
    return this.http.post<IPessoa>(`${this.pathBase}pessoas/cadastrar`,pessoa)
  }

  putPessoa(pessoa: IPessoa): Observable<IPessoa>{
    return this.http.put<IPessoa>(`${this.pathBase}pessoas/pessoa/editar`,pessoa);
  }

  detePessoa(id:string): Observable<Boolean>{
    return this.http.delete<Boolean>(`${this.pathBase}pessoas/pessoa/excluir?id=${id}`);
  }
}
