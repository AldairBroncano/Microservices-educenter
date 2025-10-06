import { Component, OnInit } from '@angular/core';
import { CourseService } from './course.service';
import { AuthService } from '../../core/services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  imports: [CommonModule],
})
export class CourseListComponent implements OnInit {
  courses: any[] = [];
  roles: string[] = [];
  user: any;

  constructor(private cs: CourseService, public auth: AuthService) {}

  ngOnInit() {
    this.load();
    this.roles = this.auth.getUserRoles();

    // Obtener usuario actual del observable
    this.auth.user$.subscribe((u) => {
      this.user = u;
    });
  }

  load() {
    this.cs.getAll().subscribe((res) => (this.courses = res));
  }

  canEdit(course: any) {
    if (this.roles.includes('ADMIN')) return true;
    if (this.roles.includes('TEACHER') && this.user) {
      return this.user.id === course.teacherId;
    }
    return false;
  }

  editCourse(course: any) {
    // Aquí puedes redirigir a un formulario o abrir un modal
    console.log('Editar curso:', course);
  }

  deleteCourse(id: number) {
    if (confirm('¿Seguro que deseas eliminar este curso?')) {
      this.cs.delete(id).subscribe(() => {
        this.load(); // recargar lista después de borrar
      });
    }
  }
}
