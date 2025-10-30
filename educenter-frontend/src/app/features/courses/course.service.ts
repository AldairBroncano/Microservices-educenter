import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AuthService } from '../../../app/core/services/auth.service';

@Injectable({ providedIn: 'root' })
export class CourseService {
  private base = `${environment.apiUrl}/courses`;

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  getAll(): Observable<any> {
    return this.http.get(this.base, { headers: this.getHeaders() });
  }

  getById(id: number) {
    return this.http.get(`${this.base}/${id}`, { headers: this.getHeaders() });
  }

  getByTeacher(teacherId: number) {
    return this.http.get(`${this.base}/teacher/${teacherId}`, {
      headers: this.getHeaders(),
    });
  }

  create(course: any) {
    return this.http.post(this.base, course, { headers: this.getHeaders() });
  }

  update(id: number, course: any) {
    return this.http.put(`${this.base}/${id}`, course, {
      headers: this.getHeaders(),
    });
  }

  delete(id: number) {
    return this.http.delete(`${this.base}/${id}`, {
      headers: this.getHeaders(),
    });
  }

  enroll(courseId: number, id: number) {
    return this.http.post(
      `${this.base}/${courseId}/enroll/${id}`,
      {},
      {
        headers: this.getHeaders(),
      }
    );
  }

  // ✅ Asignar profesor a un curso
  assignTeacher(courseId: number, teacherId: number): Observable<any> {
    return this.http.post(
      `${this.base}/${courseId}/assign/${teacherId}`,
      {},
      {
        headers: this.getHeaders(),
        responseType: 'text',
      }
    );
  }
}
