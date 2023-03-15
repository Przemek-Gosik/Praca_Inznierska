import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FindingNumbersComponent } from './finding-numbers.component';

describe('FindingNumbersComponent', () => {
  let component: FindingNumbersComponent;
  let fixture: ComponentFixture<FindingNumbersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FindingNumbersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FindingNumbersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
