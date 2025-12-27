import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService, Keynote } from '../../services/api.service';

@Component({
  selector: 'app-keynotes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './keynotes.component.html',
  styleUrl: './keynotes.component.css'
})
export class KeynotesComponent implements OnInit {
  keynotes: Keynote[] = [];
  errorMessage = '';
  statusMessage = '';
  loadingKeynotes = false;

  keynoteForm: Keynote = {
    nom: '',
    prenom: '',
    email: '',
    fonction: ''
  };
  editingId: number | null = null;

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.loadKeynotes();
  }

  loadKeynotes(): void {
    this.loadingKeynotes = true;
    this.errorMessage = '';
    this.api.getKeynotes().subscribe({
      next: (data) => {
        this.keynotes = data ?? [];
        this.loadingKeynotes = false;
      },
      error: () => {
        this.loadingKeynotes = false;
        this.errorMessage = 'Failed to load keynotes. Is keynote-service running on 8082?';
      }
    });
  }

  addKeynote(): void {
    if (!this.keynoteForm.nom || !this.keynoteForm.prenom) {
      this.statusMessage = 'Please provide at least name and surname for a keynote.';
      return;
    }
    if (this.editingId != null) {
      this.api.updateKeynote(this.editingId, this.keynoteForm).subscribe({
        next: () => {
          this.statusMessage = 'Keynote updated.';
          this.resetForm();
          this.loadKeynotes();
        },
        error: () => {
          this.statusMessage = 'Unable to update keynote.';
        }
      });
    } else {
      this.api.createKeynote(this.keynoteForm).subscribe({
        next: () => {
          this.statusMessage = 'Keynote created.';
          this.resetForm();
          this.loadKeynotes();
        },
        error: () => {
          this.statusMessage = 'Unable to create keynote.';
        }
      });
    }
  }

  deleteKeynote(id?: number): void {
    if (id == null) {
      return;
    }
    this.api.deleteKeynote(id).subscribe({
      next: () => {
        this.statusMessage = 'Keynote deleted.';
        this.loadKeynotes();
      },
      error: () => {
        this.statusMessage = 'Unable to delete keynote.';
      }
    });
  }

  startEdit(keynote: Keynote): void {
    if (keynote.id == null) {
      return;
    }
    this.editingId = keynote.id;
    this.keynoteForm = {
      nom: keynote.nom,
      prenom: keynote.prenom,
      email: keynote.email,
      fonction: keynote.fonction
    };
  }

  cancelEdit(): void {
    this.resetForm();
  }

  private resetForm(): void {
    this.editingId = null;
    this.keynoteForm = { nom: '', prenom: '', email: '', fonction: '' };
  }

  trackById(_: number, item: { id?: number }): number | undefined {
    return item.id;
  }
}
