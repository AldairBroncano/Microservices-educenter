import { Injectable } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private base = `${environment.apiUrl}/user`;

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  getAllFullProfiles(): Observable<any[]> {
    return this.http.get<any[]>(`${this.base}/full-profiles`, {
      headers: this.getHeaders(),
    });
  }

  getFullProfilesById(id: number): Observable<any> {
    return this.http.get<any>(`${this.base}/full-profiles/${id}`, {
      headers: this.getHeaders(),
    });
  }
}
