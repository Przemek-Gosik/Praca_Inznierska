import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-finding-numbers',
  templateUrl: './finding-numbers.component.html',
  styleUrls: ['./finding-numbers.component.css']
})
export class FindingNumbersComponent implements OnInit {
  level: string =""
  constructor() { }

  ngOnInit(): void {
  }

}
