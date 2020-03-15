import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class VendaService {

  private urlApi = `${environment.urlApi}/api/venda`;

  constructor(private http: HttpClient) { }

  getQuantiddeVendasPorUsuario(): Observable<number> {
    const id = localStorage.getItem('userID');
    return this.http.get<number>(`${this.urlApi}/venda/quantidade/${id}`);
  }

}
