var browsersync = require('browser-sync').create();
var gulp = require('gulp');
var sass = require('gulp-sass');

var PATH = {
    HTML: './src/main/resources/templates',
    CSS: './src/main/resources/static/css',
    SCSS: './src/main/resources/static/scss',
};
// init Browser-Sync
// watch files for changes
gulp.task('watch', () => {
    return new Promise(resolve => {
        gulp.watch(['src/main/resources/templates/**/*.html'], gulp.series['html']).on('change', browsersync.reload);
        gulp.watch(['src/main/resources/static/**/*.css'], gulp.series['css']).on('change', browsersync.reload);
        gulp.watch(['src/main/resources/static/**/*.scss'], gulp.series['scss']).on('change', browsersync.reload);
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
gulp.task('scss', () => {
    return new Promise(resolve => {
        gulp.src(PATH.SCSS + '/*.scss')
            .pipe(sass().on('error', sass.logError))
            .pipe(gulp.dest('src/main/resources/static/css'));
        resolve();
    });
});


gulp.task('browser-sync', () => {
    return new Promise(resolve => {
        browsersync.init(null, {
            proxy: "http://localhost:8100/test",
            port: 8001,
            files: ['src/main/resources/templates/**/*.html', 'src/main/resources/static/**/*.css', 'src/main/resources/static/**/*.scss']
        });
        resolve();
    });
});


// default task
gulp.task('default', gulp.series('browser-sync', 'watch', 'html', 'css', 'scss'));
