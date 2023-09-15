import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BODY_PATH } from 'src/app/environments/environment';
import { IContato } from 'src/app/interfaces/IContato';

@Injectable({
  providedIn: 'root'
})
export class ContatoService {
  private pathBase = API_BODY_PATH;
  constructor(private http: HttpClient) { }

  getContatoById(id:string): Observable<IContato>{
    return this.http.get<IContato>(`${this.pathBase}contatos/contato?id=${id}`);
  }

  getUltimoIndice(): Observable<IContato>{
    return this.http.get<IContato>(`${this.pathBase}contatos/contato/ultimoId`);
  }

  postContato(contato: IContato): Observable<IContato>{
    return this.http.post<IContato>(`${this.pathBase}contatos/contato/cadastrar`,contato)
  }

  putContato(contato: IContato): Observable<IContato>{
    return this.http.put<IContato>(`${this.pathBase}contatos/contato/editar`,contato);
  }

  deteContato(id:string): Observable<Boolean>{
    return this.http.delete<Boolean>(`${this.pathBase}contatos/contato/excluir?id=${id}`);
  }
}
