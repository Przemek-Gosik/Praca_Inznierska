import { Component, OnInit, ViewChild } from "@angular/core";
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { Report } from "src/app/models/report";
import { ReportService } from "src/app/services/report.service";
import { TextService } from "src/app/services/text.service";

@Component({
  selector: "app-reports",
  templateUrl: "./reports.component.html",
  styleUrls: ["./reports.component.css"],
})
export class ReportsComponent implements OnInit {
  reports: Report[] = [];
  dataSource = new MatTableDataSource<Report>();
  @ViewChild(MatPaginator) paginator?: MatPaginator;
  displayedColumns: string[] = ["number", "title", "email", "date", "action"];

  constructor(
    private reportService: ReportService,
    private textService: TextService
  ) {}

  ngOnInit(): void {
    this.reportService.getAllReports().subscribe((res:Report[])=>{
      this.reports = res
      this.dataSource.data = this.reports
      this.dataSource.paginator = this.paginator!
    })
  }

  showDetails(report:Report): void {
    
  }

  showDate(date:string){
    return this.textService.showDate(date)
  }
}
