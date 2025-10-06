import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class CourseService {
  private base = `${environment.apiUrl}/courses`;
  constructor(private http: HttpClient) {}

  getAll(): Observable<any> {
    return this.http.get(this.base);
  }
  getById(id: number) {
    return this.http.get(`${this.base}/${id}`);
  }
  getByTeacher(teacherId: number) {
    return this.http.get(`${this.base}/teacher/${teacherId}`);
  }
  create(course: any) {
    return this.http.post(this.base, course);
  }
  update(id: number, course: any) {
    return this.http.put(`${this.base}/${id}`, course);
  }
  delete(id: number) {
    return this.http.delete(`${this.base}/${id}`);
  }
}
