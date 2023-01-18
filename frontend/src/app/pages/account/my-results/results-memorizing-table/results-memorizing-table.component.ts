import { AfterViewInit, Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MemorizngResult } from 'src/app/models/memorizing-model';
import { TextService } from 'src/app/services/text.service';

@Component({
  selector: 'app-results-memorizing-table',
  templateUrl: './results-memorizing-table.component.html',
  styleUrls: ['./results-memorizing-table.component.css']
})
export class ResultsMemorizingTableComponent implements OnInit, OnChanges, AfterViewInit {

  @Input() results?: MemorizngResult[];
  @ViewChild(MatPaginator) paginator?: MatPaginator;
  displayedColumns: string[] = ['score','level','startTime'];
  dataSource = new MatTableDataSource<MemorizngResult>();
  selectedValue:string = "MEMORY";
  
  constructor(private textService: TextService) { }

  ngOnChanges(changes: SimpleChanges): void {
    if(this.results){
      this.dataSource.data = this.results
    }
  }
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator!
  }

  ngOnInit(): void {
    this.dataSource.filterPredicate = (data:MemorizngResult,filter:string) => data.type == filter;
  }

  showLevel(level:string):string{
    return this.textService.getLevelName(level)
  }

  valueChange(value: string):void{
    this.selectedValue = value
    this.dataSource.filter = this.selectedValue
  }

  showDate(date:string):string{
    const dateTime = new Date(date);
    return `${dateTime.toLocaleDateString()} ${dateTime.toLocaleTimeString()}`
  }

}
