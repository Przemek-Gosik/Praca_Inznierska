import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultsMemorizingTableComponent } from './results-memorizing-table.component';

describe('ResultsMemorizingTableComponent', () => {
  let component: ResultsMemorizingTableComponent;
  let fixture: ComponentFixture<ResultsMemorizingTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResultsMemorizingTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResultsMemorizingTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
