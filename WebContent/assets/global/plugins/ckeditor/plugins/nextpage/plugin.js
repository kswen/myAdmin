/**
 * Basic sample plugin inserting current date and time into CKEditor editing area.
 *
 * Created out of the CKEditor Plugin SDK:
 * http://docs.ckeditor.com/#!/guide/plugin_sdk_intro
 */

// Register the plugin within the editor.
CKEDITOR.plugins.add( 'nextpage', {

	// Register the icons. They must match command names.
	icons: 'nextpage',

	// The plugin initialization logic goes inside this method.
	init: function( editor ) {

		// Define an editor command that inserts a nextpage.
		editor.addCommand( 'insertNextPage', {

			// Define the function that will be fired when the command is executed.
			exec: function( editor ) {
				var now = new Date();

				// Insert the NextPage into the document.
				editor.insertHtml( '<p>[NextPage][/NextPage]</p>' );
			}
		});

		// Create the toolbar button that executes the above command.
		editor.ui.addButton( 'NextPage', {
			label: 'Insert NextPage',
			command: 'insertNextPage',
			toolbar: 'insert'
		});
	}
});