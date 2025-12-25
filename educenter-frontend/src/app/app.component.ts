import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthService } from './core/services/auth.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App implements OnInit {
  protected readonly title = signal('educenter-frontend');

  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.authService.restoreUserFromStorage(); // 🔥 RESTAURA LA SESIÓN
  }
}
