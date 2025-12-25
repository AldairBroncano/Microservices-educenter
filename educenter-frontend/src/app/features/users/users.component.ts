import { Component, OnInit } from '@angular/core';
import { UserService } from './user.service';
import { AuthService } from '../../core/services/auth.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-users',
  imports: [CommonModule, RouterModule],
  templateUrl: './users.html',
  styleUrl: './users.scss',
  standalone: true,
})
export class UsersComponent implements OnInit {
  users$!: Observable<any[]>;
  role: string | null = null;

  constructor(private userService: UserService, private authService: AuthService) {}

  ngOnInit(): void {
    // obtenemos el role de manera inmediata
    this.role = this.authService.getRole();

    if (this.isAuthorized()) {
      // usamos observable directamente
      this.users$ = this.userService.getAllFullProfiles();
    }
  }

  isAuthorized(): boolean {
    return this.role === 'ADMIN' || this.role === 'TEACHER';
  }
}
