import { Routes } from '@angular/router';
import { CourseListComponent } from './features/courses/course-list.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { AdminDashboard } from './features/dashboard/admin-dashboard/admin-dashboard';
import { TeacherDashboard } from './features/dashboard/teacher-dashboard/teacher-dashboard';
import { StudentDashboard } from './features/dashboard/student-dashboard/student-dashboard';
import { AuthGuard } from './core/guards/auth.guard';
import { RoleGuard } from './core/guards/role.guard';
import { CourseEdit } from './features/courses/course-edit/course-edit.component';
import { UsersComponent } from './features/users/users.component';
import { Unauthorized } from './features/unauthorized/unauthorized';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'users',
    component: UsersComponent,
    canActivate: [RoleGuard, AuthGuard], // ⛔ Bloquea si no tiene rol
    data: { roles: ['ADMIN', 'TEACHER'] },
  },

  { path: 'unauthorized', component: Unauthorized },

  // dashboards
  { path: 'admin-dashboard', component: AdminDashboard, canActivate: [AuthGuard] },
  { path: 'teacher-dashboard', component: TeacherDashboard, canActivate: [AuthGuard] },
  { path: 'student-dashboard', component: StudentDashboard, canActivate: [AuthGuard] },

  // cursos
  { path: 'courses', component: CourseListComponent, canActivate: [AuthGuard] },
  { path: 'courses/edit/:id', component: CourseEdit },

  // fallback
  { path: '**', redirectTo: 'login' },
];
