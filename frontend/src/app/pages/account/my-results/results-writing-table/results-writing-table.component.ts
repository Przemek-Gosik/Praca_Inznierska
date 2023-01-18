import { AfterViewInit, Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { WritingTextResult } from 'src/app/models/writing-model';
import { TextService } from 'src/app/services/text.service';

@Component({
  selector: 'app-results-writing-table',
  templateUrl: './results-writing-table.component.html',
  styleUrls: ['./results-writing-table.component.css']
})
export class ResultsWritingTableComponent implements OnInit,OnChanges,AfterViewInit{

  @Input('results') results?: WritingTextResult[];
  @ViewChild(MatPaginator) paginator?: MatPaginator;
  displayedColumns: string[] = ['title','score','time','startTime'];
  dataSource = new MatTableDataSource<WritingTextResult>();
  constructor(private textService: TextService) { }
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator!
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(this.results){
      this.dataSource.data=this.results
    }
  }

  ngOnInit(): void {
    
  }

  showDate(date:string):string{
    return this.textService.showDate(date)
  }

}
