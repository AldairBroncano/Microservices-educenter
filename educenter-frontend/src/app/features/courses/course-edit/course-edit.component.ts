import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseService } from '../course.service';

@Component({
  selector: 'app-course-edit',
  standalone: true,
  imports: [FormsModule, NgIf],

  templateUrl: './course-edit.html',
  styleUrls: ['./course-edit.scss'],
})
export class CourseEdit {
  courseId!: number;
  course: any = {};

  constructor(private route: ActivatedRoute, private cs: CourseService, private router: Router) {}

  ngOnInit(): void {
    this.courseId = Number(this.route.snapshot.paramMap.get('id'));
    this.cs.getById(this.courseId).subscribe((data) => {
      this.course = data;
    });
  }

  onSubmit(): void {
    this.cs.update(this.courseId, this.course).subscribe({
      next: () => {
        alert('Curso actualizado correctamente');
        this.router.navigate(['/courses']);
      },
      error: (err) => {
        alert('Error al actualizar ' + err.message);
      },
    });
  }

  goback() {
    this.router.navigate(['/courses']);
  }
}
