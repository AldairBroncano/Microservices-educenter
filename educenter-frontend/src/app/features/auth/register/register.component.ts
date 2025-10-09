import { Component } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.html',
  styleUrls: ['./register.scss'],
  imports: [CommonModule, FormsModule, RouterModule],
})
export class RegisterComponent {
  payload = { username: '', email: '', password: '' };

  constructor(private authService: AuthService) {}

  onRegister() {
    this.authService.register(this.payload).subscribe({
      next: (res) => {
        console.log('Usuario registrado', res);
        alert('Registro exitoso, ahora puedes iniciar sesión');
      },
      error: (err) => {
        console.error('Error en registro', err);
        alert('No se pudo registrar');
      },
    });
  }
}
