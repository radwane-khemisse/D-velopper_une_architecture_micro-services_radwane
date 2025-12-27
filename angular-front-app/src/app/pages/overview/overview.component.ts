import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-overview',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './overview.component.html',
  styleUrl: './overview.component.css'
})
export class OverviewComponent implements OnInit {
  keynotesCount = 0;
  conferencesCount = 0;
  errorMessage = '';
  statusMessage = '';

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.loadKeynotesCount();
    this.loadConferencesCount();
  }

  private loadKeynotesCount(): void {
    this.api.getKeynotes().subscribe({
      next: (data) => {
        this.keynotesCount = data?.length ?? 0;
      },
      error: () => {
        this.keynotesCount = 0;
        this.errorMessage = 'Unable to reach keynote-service on port 8082.';
      }
    });
  }

  private loadConferencesCount(): void {
    this.api.getConferences().subscribe({
      next: (data) => {
        this.conferencesCount = data?.length ?? 0;
      },
      error: () => {
        this.conferencesCount = 0;
        this.errorMessage = this.errorMessage || 'Unable to reach conference-service on port 8083.';
      }
    });
  }
}
