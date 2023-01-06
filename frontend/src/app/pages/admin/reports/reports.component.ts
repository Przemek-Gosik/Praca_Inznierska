import { Component, OnInit } from '@angular/core';
import { Report } from 'src/app/models/report';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css']
})
export class ReportsComponent implements OnInit {

  reports : Report[] = [] 
  
  constructor() { }

  ngOnInit(): void {
  }

}
