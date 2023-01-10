import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchulzComponent } from './schulz.component';

describe('SchubertComponent', () => {
  let component: SchulzComponent;
  let fixture: ComponentFixture<SchulzComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SchulzComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SchulzComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
