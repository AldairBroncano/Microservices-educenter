import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { UserService } from './user.service';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-users',
  imports: [CommonModule, RouterModule],
  templateUrl: './users.html',
  styleUrl: './users.scss',
  standalone: true,
})
export class UsersComponent implements OnInit {
  users: any[] = [];

  constructor(private userService: UserService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.userService.getAllFullProfiles().subscribe((data) => {
      this.users = data;
      this.cdr.detectChanges(); // 👈 evita NG0100
    });
  }
}
