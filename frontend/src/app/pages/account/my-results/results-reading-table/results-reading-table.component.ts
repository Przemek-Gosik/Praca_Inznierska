import { Component, Input, OnInit ,ViewChild,OnChanges,AfterViewInit} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ReadingResult } from 'src/app/models/reading-model';

@Component({
  selector: 'app-results-reading-table',
  templateUrl: './results-reading-table.component.html',
  styleUrls: ['./results-reading-table.component.css']
})
export class ResultsReadingTableComponent implements OnInit,OnChanges,AfterViewInit {
  
  @Input() results?: ReadingResult[];
  @ViewChild(MatPaginator) paginator?: MatPaginator;
  displayedColumns: string[] = ['score','time','startTime','level'];
  dataSource = new MatTableDataSource<ReadingResult>();
  selectedValue: string ="SCHULTZ"
  constructor() { }

  ngOnInit(): void {
    this.dataSource.filterPredicate=(data:ReadingResult,filter:string)=>data.type==filter;
    this.dataSource.filter=this.selectedValue
  }
  ngOnChanges():void{
    if(this.results){
      this.dataSource.data = this.results
    }
  }
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator!
  }

  showLevel(level:string):string{
    switch(level){
      case "EASY":{
        return "Łatwy"
      }
      case "MEDIUM":{
        return "Średni"
      }
      default:{
        return "Trudny"
      }
    }
  }

  valueChange(value:string){
    this.selectedValue=value
    this.dataSource.filter=this.selectedValue
  }

  showDate(date:string):string{
    const dateTime = new Date(date);
    return `${dateTime.toLocaleDateString()} ${dateTime.toLocaleTimeString()}`
  }

}
