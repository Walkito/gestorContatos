import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BODY_PATH } from 'src/app/environments/environment';
import { IContatoArr } from 'src/app/interfaces/IContatoArr';

@Injectable({
  providedIn: 'root'
})
export class ContatosService {
  private pathBase = API_BODY_PATH;

  constructor(private http:HttpClient) { }


  getContatoFiltrado(parametros: { [key: string]: string}): Observable<IContatoArr>{
    let params = this.criarParametros(parametros);

    return this.http.get<IContatoArr>(`${this.pathBase}contatos`, {params})
  }

  criarParametros(params: { [key: string]: string}): HttpParams{
    let parametros = new HttpParams();
    for (const key in params){
      if (params.hasOwnProperty(key)) {
        parametros = parametros.set(key, params[key]);
      }
    }
    return parametros;
  }
}
