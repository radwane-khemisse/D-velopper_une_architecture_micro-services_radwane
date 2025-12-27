import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  navLinks = [
    { path: '/overview', label: 'Overview' },
    { path: '/keynotes', label: 'Keynotes' },
    { path: '/conferences', label: 'Conferences' }
  ];

  trackByPath(_: number, item: { path: string }): string {
    return item.path;
  }
}
