import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultsWritingTableComponent } from './results-writing-table.component';

describe('ResultsWritingTableComponent', () => {
  let component: ResultsWritingTableComponent;
  let fixture: ComponentFixture<ResultsWritingTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResultsWritingTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResultsWritingTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
