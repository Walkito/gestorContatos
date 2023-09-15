import { Injectable } from '@angular/core';
import { HttpClient, HttpParams} from '@angular/common/http';
import { API_BODY_PATH } from 'src/app/environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { IPessoa } from 'src/app/interfaces/IPessoa';
import { IPessoaArr } from 'src/app/interfaces/IPessoaArr';
 
@Injectable({
  providedIn: 'root'
})
export class PessoaService {
  private pathBase = API_BODY_PATH;
  constructor(private http: HttpClient) { }

  getPessoaFiltrada(parametros: { [key: string]: string}): Observable<IPessoaArr>{
    let params = this.criarParametros(parametros);

    return this.http.get<IPessoaArr>(`${this.pathBase}pessoas`, {params})
  }

  private criarParametros(params: { [key: string]: string}): HttpParams{
    let parametros = new HttpParams();
    for (const key in params){
      if (params.hasOwnProperty(key)) {
        parametros = parametros.set(key, params[key]);
      }
    }
    return parametros;
  }
}
