import { Routes } from '@angular/router';
import { OverviewComponent } from './pages/overview/overview.component';
import { KeynotesComponent } from './pages/keynotes/keynotes.component';
import { ConferencesComponent } from './pages/conferences/conferences.component';
import { DiscoveryComponent } from './pages/discovery/discovery.component';
import { ConfigComponent } from './pages/config/config.component';
import { GatewayComponent } from './pages/gateway/gateway.component';

export const routes: Routes = [
  { path: '', redirectTo: 'overview', pathMatch: 'full' },
  { path: 'overview', component: OverviewComponent },
  { path: 'keynotes', component: KeynotesComponent },
  { path: 'conferences', component: ConferencesComponent },
  { path: 'discovery', component: DiscoveryComponent },
  { path: 'config', component: ConfigComponent },
  { path: 'gateway', component: GatewayComponent },
  { path: '**', redirectTo: 'overview' }
];
