import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { CourseService } from './course.service';
import { AuthService } from '../../core/services/auth.service';
import { Observable } from 'rxjs';
import { error } from 'console';

@Component({
  selector: 'app-course-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.scss'],
})
export class CourseListComponent implements OnInit {
  courses$!: Observable<any[]>; // Observable que usaremos con async pipe
  roles: string[] = [];
  user: any;

  constructor(private cs: CourseService, public auth: AuthService, private router: Router) {}

  ngOnInit() {
    this.courses$ = this.cs.getAll(); // ✅ Usa 'this.' para acceder a la propiedad
    this.roles = this.auth.getUserRoles();
    console.log('Rol del usuario:', this.roles);

    this.auth.user$.subscribe((u) => {
      this.user = u;
    });
  }

  canEdit(course: any): boolean {
    return this.roles.includes('ADMIN');
  }

  editCourse(course: any) {
    this.router.navigate(['/courses/edit', course.id]);
  }

  deleteCourse(id: number) {
    if (confirm('¿Seguro que deseas eliminar este curso?')) {
      this.cs.delete(id).subscribe(() => {
        // 🔄 Recargar los datos después de eliminar
        this.courses$ = this.cs.getAll();
      });
    }
  }

  goBack() {
    if (this.roles.includes('ADMIN')) {
      this.router.navigate(['/admin-dashboard']);
    } else if (this.roles.includes('TEACHER')) {
      this.router.navigate(['/teacher-dashboard']);
    } else if (this.roles.includes('STUDENT')) {
      this.router.navigate(['/student-dashboard']);
    } else {
      this.router.navigate(['/login']); // por si acaso no hay rol
    }
  }

  enroll(courseId: number) {
    const userId = this.auth.getUserId();
    if (!userId) {
      alert('⚠️ No se pudo obtener tu usuario. Inicia sesión de nuevo.');
      return;
    }

    this.cs.enroll(courseId, userId).subscribe(() => {
      alert('✅ Te inscribiste correctamente');
    });
  }

  assignTeacher(courseId: number) {
    const userId = this.auth.getUserId();
    if (!userId) {
      alert('⚠️ No se pudo obtener tu usuario. Inicia sesión de nuevo.');
      return;
    }

    this.cs.assignTeacher(courseId, userId).subscribe({
      next: () => alert('✅ Profesor asignado correctamente al curso'),
      error: (err) => alert('❌ Error al asignar profesor: ' + err.message),
    });
  }
}
