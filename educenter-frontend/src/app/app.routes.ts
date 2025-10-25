import { Routes } from '@angular/router';
import { CourseListComponent } from './features/courses/course-list.component';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { AdminDashboard } from './features/dashboard/admin-dashboard/admin-dashboard';
import { TeacherDashboard } from './features/dashboard/teacher-dashboard/teacher-dashboard';
import { StudentDashboard } from './features/dashboard/student-dashboard/student-dashboard';
import { AuthGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  { path: 'admin-dashboard', component: AdminDashboard, canActivate: [AuthGuard] },
  { path: 'teacher-dashboard', component: TeacherDashboard, canActivate: [AuthGuard] },
  { path: 'student-dashboard', component: StudentDashboard, canActivate: [AuthGuard] },
  { path: 'courses', component: CourseListComponent, canActivate: [AuthGuard] },
];
