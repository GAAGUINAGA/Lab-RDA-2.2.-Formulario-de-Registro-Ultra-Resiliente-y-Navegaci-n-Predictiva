# Formulario resiliente

Este proyecto usa dos mecanismos distintos para conservar los datos del formulario:

| Mecanismo | Dónde guarda el dato | Cuándo se recupera | Uso en esta práctica |
| --- | --- | --- | --- |
| `SavedStateHandle` | Estado guardado de la actividad, asociado al `ViewModel` | Al recrear la actividad por rotación o después de que el sistema destruya su proceso y restaure la tarea | Email (`email_key`) |
| DataStore | Almacenamiento persistente de la aplicación en disco | Al volver a abrir la aplicación, incluso después de cerrarla por completo | Nombre (`user_name`) |

`SavedStateHandle` sirve para conservar estado temporal de la pantalla. No sustituye a una base de datos ni garantiza recuperar el email en un inicio nuevo de la aplicación después de que la tarea se haya descartado.

DataStore guarda el nombre en disco mediante `UserPreferences`. Por eso el nombre vuelve a aparecer al abrir la aplicación de nuevo. El indicador `✓` aparece durante un segundo cuando termina correctamente cada escritura.
