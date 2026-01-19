import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import PersonForm from './PersonForm';
import { personService } from '../services/personService';

// Mock des personService
jest.mock('../services/personService', () => ({
  personService: {
    create: jest.fn(),
    update: jest.fn(),
  },
}));

describe('PersonForm Component', () => {
  
  beforeEach(() => {
    jest.clearAllMocks();
  });
  
  describe('Rendering', () => {
    it('sollte alle Formularfelder rendern', () => {
      render(<PersonForm />);
      
      expect(screen.getByLabelText(/name/i)).toBeInTheDocument();
      expect(screen.getByLabelText(/vorname/i)).toBeInTheDocument();
      expect(screen.getByLabelText(/thema/i)).toBeInTheDocument();
      expect(screen.getByLabelText(/abgabedatum/i)).toBeInTheDocument();
    });
    
    it('sollte "Neue Person erstellen" Titel zeigen wenn keine Person übergeben', () => {
      render(<PersonForm />);
      
      expect(screen.getByText(/neue person erstellen/i)).toBeInTheDocument();
    });
    
    it('sollte "Person bearbeiten" Titel zeigen wenn existingPerson übergeben', () => {
      const existingPerson = {
        id: 1,
        name: 'Mustafa',
        vorname: 'Sagaaro',
        thema: 'Test',
        abgabedatum: '2026-06-15',
      };
      
      render(<PersonForm existingPerson={existingPerson} />);
      
      expect(screen.getByText(/person bearbeiten/i)).toBeInTheDocument();
    });
    
    it('sollte Formularfelder mit existingPerson Daten vorausfüllen', () => {
      const existingPerson = {
        id: 1,
        name: 'Mustafa',
        vorname: 'Sagaaro',
        thema: 'Test Thema',
        abgabedatum: '2026-06-15',
      };
      
      render(<PersonForm existingPerson={existingPerson} />);
      
      expect(screen.getByDisplayValue('Mustafa')).toBeInTheDocument();
      expect(screen.getByDisplayValue('Sagaaro')).toBeInTheDocument();
      expect(screen.getByDisplayValue('Test Thema')).toBeInTheDocument();
    });
  });
  
  describe('Form Interactions', () => {
    it('sollte Eingaben aktualisieren bei Benutzeränderungen', async () => {
      render(<PersonForm />);
      
      const nameInput = screen.getByLabelText(/name/i);
      await userEvent.type(nameInput, 'TestName');
      
      expect(nameInput.value).toBe('TestName');
    });
    
    it('sollte Submit-Button deaktivieren während Laden', async () => {
      personService.create.mockImplementation(() => new Promise(() => {})); // Never resolves
      
      render(<PersonForm />);
      
      // Formular ausfüllen
      await userEvent.type(screen.getByLabelText(/name/i), 'Test');
      await userEvent.type(screen.getByLabelText(/vorname/i), 'User');
      await userEvent.type(screen.getByLabelText(/thema/i), 'Thema');
      await userEvent.type(screen.getByLabelText(/abgabedatum/i), '2026-06-15');
      
      // Submit
      const submitButton = screen.getByRole('button', { name: /erstellen/i });
      fireEvent.click(submitButton);
      
      // Button sollte disabled sein während Laden
      expect(submitButton).toBeDisabled();
    });
  });
  
  describe('Form Submission', () => {
    it('sollte personService.create aufrufen bei neuem Person Submit', async () => {
      const mockPerson = {
        id: 1,
        name: 'Neu',
        vorname: 'Person',
        thema: 'Neues Thema',
        abgabedatum: '2026-06-15',
      };
      personService.create.mockResolvedValue(mockPerson);
      
      const onPersonCreated = jest.fn();
      render(<PersonForm onPersonCreated={onPersonCreated} />);
      
      // Formular ausfüllen
      await userEvent.type(screen.getByLabelText(/name/i), 'Neu');
      await userEvent.type(screen.getByLabelText(/vorname/i), 'Person');
      await userEvent.type(screen.getByLabelText(/thema/i), 'Neues Thema');
      await userEvent.type(screen.getByLabelText(/abgabedatum/i), '2026-06-15');
      
      // Submit
      fireEvent.click(screen.getByRole('button', { name: /erstellen/i }));
      
      await waitFor(() => {
        expect(personService.create).toHaveBeenCalledWith({
          name: 'Neu',
          vorname: 'Person',
          thema: 'Neues Thema',
          abgabedatum: '2026-06-15',
        });
      });
    });
    
    it('sollte Erfolgsmeldung zeigen nach erfolgreichem Erstellen', async () => {
      personService.create.mockResolvedValue({ id: 1, name: 'Test' });
      
      render(<PersonForm />);
      
      // Formular ausfüllen
      await userEvent.type(screen.getByLabelText(/name/i), 'Test');
      await userEvent.type(screen.getByLabelText(/vorname/i), 'User');
      await userEvent.type(screen.getByLabelText(/thema/i), 'Thema');
      await userEvent.type(screen.getByLabelText(/abgabedatum/i), '2026-06-15');
      
      // Submit
      fireEvent.click(screen.getByRole('button', { name: /erstellen/i }));
      
      await waitFor(() => {
        expect(screen.getByText(/erfolgreich erstellt/i)).toBeInTheDocument();
      });
    });
    
    it('sollte Fehlermeldung zeigen bei API-Fehler', async () => {
      personService.create.mockRejectedValue({ 
        response: { status: 500 } 
      });
      
      render(<PersonForm />);
      
      // Formular ausfüllen
      await userEvent.type(screen.getByLabelText(/name/i), 'Test');
      await userEvent.type(screen.getByLabelText(/vorname/i), 'User');
      await userEvent.type(screen.getByLabelText(/thema/i), 'Thema');
      await userEvent.type(screen.getByLabelText(/abgabedatum/i), '2026-06-15');
      
      // Submit
      fireEvent.click(screen.getByRole('button', { name: /erstellen/i }));
      
      await waitFor(() => {
        expect(screen.getByText(/serverfehler/i)).toBeInTheDocument();
      });
    });
    
    it('sollte personService.update aufrufen bei existingPerson', async () => {
      const existingPerson = {
        id: 1,
        name: 'Alt',
        vorname: 'Name',
        thema: 'Altes Thema',
        abgabedatum: '2026-01-01',
      };
      personService.update.mockResolvedValue({ ...existingPerson, name: 'Neu' });
      
      render(<PersonForm existingPerson={existingPerson} />);
      
      // Namen ändern
      const nameInput = screen.getByLabelText(/name/i);
      await userEvent.clear(nameInput);
      await userEvent.type(nameInput, 'Neu');
      
      // Submit
      fireEvent.click(screen.getByRole('button', { name: /aktualisieren/i }));
      
      await waitFor(() => {
        expect(personService.update).toHaveBeenCalledWith(1, expect.objectContaining({
          name: 'Neu',
        }));
      });
    });
    
    it('sollte onPersonCreated Callback aufrufen nach Erstellen', async () => {
      const mockPerson = { id: 1, name: 'Test' };
      personService.create.mockResolvedValue(mockPerson);
      const onPersonCreated = jest.fn();
      
      render(<PersonForm onPersonCreated={onPersonCreated} />);
      
      // Formular ausfüllen und absenden
      await userEvent.type(screen.getByLabelText(/name/i), 'Test');
      await userEvent.type(screen.getByLabelText(/vorname/i), 'User');
      await userEvent.type(screen.getByLabelText(/thema/i), 'Thema');
      await userEvent.type(screen.getByLabelText(/abgabedatum/i), '2026-06-15');
      fireEvent.click(screen.getByRole('button', { name: /erstellen/i }));
      
      await waitFor(() => {
        expect(onPersonCreated).toHaveBeenCalledWith(mockPerson);
      });
    });
    
    it('sollte Formular zurücksetzen nach erfolgreichem Erstellen', async () => {
      personService.create.mockResolvedValue({ id: 1 });
      
      render(<PersonForm />);
      
      // Formular ausfüllen
      await userEvent.type(screen.getByLabelText(/name/i), 'Test');
      await userEvent.type(screen.getByLabelText(/vorname/i), 'User');
      await userEvent.type(screen.getByLabelText(/thema/i), 'Thema');
      await userEvent.type(screen.getByLabelText(/abgabedatum/i), '2026-06-15');
      
      // Submit
      fireEvent.click(screen.getByRole('button', { name: /erstellen/i }));
      
      await waitFor(() => {
        expect(screen.getByLabelText(/name/i).value).toBe('');
      });
    });
  });
  
  describe('Error Handling', () => {
    it('sollte 400 Fehler korrekt anzeigen', async () => {
      personService.create.mockRejectedValue({ 
        response: { status: 400 } 
      });
      
      render(<PersonForm />);
      
      await userEvent.type(screen.getByLabelText(/name/i), 'Test');
      await userEvent.type(screen.getByLabelText(/vorname/i), 'User');
      await userEvent.type(screen.getByLabelText(/thema/i), 'Thema');
      await userEvent.type(screen.getByLabelText(/abgabedatum/i), '2026-06-15');
      fireEvent.click(screen.getByRole('button', { name: /erstellen/i }));
      
      await waitFor(() => {
        expect(screen.getByText(/ungültige daten/i)).toBeInTheDocument();
      });
    });
    
    it('sollte 404 Fehler korrekt anzeigen', async () => {
      personService.create.mockRejectedValue({ 
        response: { status: 404 } 
      });
      
      render(<PersonForm />);
      
      await userEvent.type(screen.getByLabelText(/name/i), 'Test');
      await userEvent.type(screen.getByLabelText(/vorname/i), 'User');
      await userEvent.type(screen.getByLabelText(/thema/i), 'Thema');
      await userEvent.type(screen.getByLabelText(/abgabedatum/i), '2026-06-15');
      fireEvent.click(screen.getByRole('button', { name: /erstellen/i }));
      
      await waitFor(() => {
        expect(screen.getByText(/nicht gefunden/i)).toBeInTheDocument();
      });
    });
  });
});



