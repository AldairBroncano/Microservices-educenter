import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-teacher-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './teacher-dashboard.html',
  styleUrl: './teacher-dashboard.scss',
})
export class TeacherDashboard {
  constructor(public authService: AuthService) {}

  logout() {
    this.authService.logout();
  }
}
