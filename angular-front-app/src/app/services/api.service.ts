import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Keynote {
  id?: number;
  nom: string;
  prenom: string;
  email: string;
  fonction: string;
}

export interface Review {
  id?: number;
  date: string;
  texte: string;
  stars: number;
}

export type ConferenceType = 'ACADEMIC' | 'COMMERCIAL';

export interface Conference {
  id?: number;
  titre: string;
  type: ConferenceType;
  date: string;
  duree: number;
  nombreInscrits: number;
  score: number;
  keynoteId?: number | null;
  reviews?: Review[];
}

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private readonly keynoteBase = 'http://localhost:8082/api/keynotes';
  private readonly conferenceBase = 'http://localhost:8083/api/conferences';

  constructor(private http: HttpClient) {}

  getKeynotes(): Observable<Keynote[]> {
    return this.http.get<Keynote[]>(this.keynoteBase);
  }

  createKeynote(payload: Keynote): Observable<Keynote> {
    return this.http.post<Keynote>(this.keynoteBase, payload);
  }

  deleteKeynote(id: number): Observable<void> {
    return this.http.delete<void>(`${this.keynoteBase}/${id}`);
  }

  updateKeynote(id: number, payload: Keynote): Observable<Keynote> {
    return this.http.put<Keynote>(`${this.keynoteBase}/${id}`, payload);
  }

  getConferences(): Observable<Conference[]> {
    return this.http.get<Conference[]>(this.conferenceBase);
  }

  createConference(payload: Conference): Observable<Conference> {
    return this.http.post<Conference>(this.conferenceBase, payload);
  }

  deleteConference(id: number): Observable<void> {
    return this.http.delete<void>(`${this.conferenceBase}/${id}`);
  }

  updateConference(id: number, payload: Conference): Observable<Conference> {
    return this.http.put<Conference>(`${this.conferenceBase}/${id}`, payload);
  }

  addReview(conferenceId: number, payload: Review): Observable<Review> {
    return this.http.post<Review>(`${this.conferenceBase}/${conferenceId}/reviews`, payload);
  }
}
