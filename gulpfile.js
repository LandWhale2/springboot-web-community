var browsersync = require('browser-sync');
var gulp = require('gulp');

var PATH = {
    HTML: './src/main/resources/templates',
    CSS: './src/main/resources/static/css'
};
// init Browser-Sync
// watch files for changes
gulp.task('watch', () => {
    return new Promise(resolve => {
        gulp.watch(['src/main/resources/templates/**/*.html'], gulp.series['html']).on('change', browsersync.reload);
        gulp.watch(['src/main/resources/static/**/*.css'], gulp.series['css']).on('change', browsersync.reload);
        resolve();
    });
});


gulp.task('html', () => {
    return new Promise(resolve => {
        gulp.src(PATH.HTML + '/*.html')
            .pipe(browsersync.reload({stream: true}));
        resolve();
    });
});
gulp.task('css', () => {
    return new Promise(resolve => {
        gulp.src(PATH.CSS + '/*.css')
            .pipe(browsersync.reload({stream: true}));
        resolve();
    });
});


gulp.task('browser-sync', () => {
    return new Promise(resolve => {
        browsersync.init(null, {
            proxy: "http://localhost:8000/test",
            port: 8006,
            files: ['src/main/resources/templates/**/*.html', 'src/main/resources/static/**/*.css']
        })
        resolve();
    });
});


// default task
gulp.task('default', gulp.series('browser-sync', 'watch', 'html', 'css'));
