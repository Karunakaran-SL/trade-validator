import { Component } from '@angular/core';
import {Trade} from './trade';
import {Http, Headers, RequestOptions} from '@angular/http';
import 'rxjs/add/operator/map';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Trade Validator';
  trade = new Trade();
  result = '';

  constructor(private http: Http) {

  }
  private getRequestOptions() {
    const headers = new Headers({'Content-Type': 'application/json'});
    return new RequestOptions({headers: headers});
  }

  validate() {
    const body = JSON.stringify(this.trade);
    this.http.post('/api/trade/valid', body, this.getRequestOptions()).map(res => res.text()).subscribe(res => {
      this.result = res;
    }, err => this.handleError(err));
  }

  handleError(err: any): void {
    alert(err);
  }
}
