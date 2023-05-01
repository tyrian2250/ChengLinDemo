import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNoteBoardComponent } from './add-note-board.component';

describe('AddNoteBoardComponent', () => {
  let component: AddNoteBoardComponent;
  let fixture: ComponentFixture<AddNoteBoardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddNoteBoardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddNoteBoardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
