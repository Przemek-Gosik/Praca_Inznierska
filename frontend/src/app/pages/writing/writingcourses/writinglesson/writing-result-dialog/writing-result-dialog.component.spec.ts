import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WritingResultDialogComponent } from './writing-result-dialog.component';

describe('WritingResultDialogComponent', () => {
  let component: WritingResultDialogComponent;
  let fixture: ComponentFixture<WritingResultDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WritingResultDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WritingResultDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
