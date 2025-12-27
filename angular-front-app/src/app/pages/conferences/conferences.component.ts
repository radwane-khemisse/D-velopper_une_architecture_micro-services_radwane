import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService, Conference, Review } from '../../services/api.service';

@Component({
  selector: 'app-conferences',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './conferences.component.html',
  styleUrl: './conferences.component.css'
})
export class ConferencesComponent implements OnInit {
  conferences: Conference[] = [];
  errorMessage = '';
  statusMessage = '';
  loadingConferences = false;
  today = new Date().toISOString().slice(0, 10);

  conferenceForm: Conference = {
    titre: '',
    type: 'ACADEMIC',
    date: this.today,
    duree: 60,
    nombreInscrits: 0,
    score: 0,
    keynoteId: null
  };
  editingId: number | null = null;

  reviewDrafts: Record<number, Review> = {};

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.loadConferences();
  }

  loadConferences(): void {
    this.loadingConferences = true;
    this.errorMessage = '';
    this.api.getConferences().subscribe({
      next: (data) => {
        this.conferences = data ?? [];
        this.conferences.forEach((conference) => {
          if (conference.id != null && !this.reviewDrafts[conference.id]) {
            this.reviewDrafts[conference.id] = {
              date: this.today,
              texte: '',
              stars: 5
            };
          }
        });
        this.loadingConferences = false;
      },
      error: () => {
        this.loadingConferences = false;
        this.errorMessage = 'Failed to load conferences. Is conference-service running on 8083?';
      }
    });
  }

  addConference(): void {
    if (!this.conferenceForm.titre || !this.conferenceForm.date) {
      this.statusMessage = 'Please provide a title and date for a conference.';
      return;
    }
    if (this.editingId != null) {
      this.api.updateConference(this.editingId, this.conferenceForm).subscribe({
        next: () => {
          this.statusMessage = 'Conference updated.';
          this.resetForm();
          this.loadConferences();
        },
        error: () => {
          this.statusMessage = 'Unable to update conference.';
        }
      });
    } else {
      this.api.createConference(this.conferenceForm).subscribe({
        next: () => {
          this.statusMessage = 'Conference created.';
          this.resetForm();
          this.loadConferences();
        },
        error: () => {
          this.statusMessage = 'Unable to create conference.';
        }
      });
    }
  }

  deleteConference(id?: number): void {
    if (id == null) {
      return;
    }
    this.api.deleteConference(id).subscribe({
      next: () => {
        this.statusMessage = 'Conference deleted.';
        this.loadConferences();
      },
      error: () => {
        this.statusMessage = 'Unable to delete conference.';
      }
    });
  }

  startEdit(conference: Conference): void {
    if (conference.id == null) {
      return;
    }
    this.editingId = conference.id;
    this.conferenceForm = {
      titre: conference.titre,
      type: conference.type,
      date: conference.date,
      duree: conference.duree,
      nombreInscrits: conference.nombreInscrits,
      score: conference.score,
      keynoteId: conference.keynoteId ?? null
    };
  }

  cancelEdit(): void {
    this.resetForm();
  }

  private resetForm(): void {
    this.editingId = null;
    this.conferenceForm = {
      titre: '',
      type: 'ACADEMIC',
      date: this.today,
      duree: 60,
      nombreInscrits: 0,
      score: 0,
      keynoteId: null
    };
  }

  addReview(conferenceId: number): void {
    const draft = this.reviewDrafts[conferenceId];
    if (!draft || !draft.texte) {
      this.statusMessage = 'Please write a review message.';
      return;
    }
    this.api.addReview(conferenceId, draft).subscribe({
      next: () => {
        this.statusMessage = 'Review added.';
        this.reviewDrafts[conferenceId] = {
          date: this.today,
          texte: '',
          stars: 5
        };
        this.loadConferences();
      },
      error: () => {
        this.statusMessage = 'Unable to add review.';
      }
    });
  }

  trackById(_: number, item: { id?: number }): number | undefined {
    return item.id;
  }
}
