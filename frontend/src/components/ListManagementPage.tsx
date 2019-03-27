import * as React from 'react';
import { useState } from 'react';
import { withRouter } from 'react-router-dom';
import {
  Card,
  CardContent,
  Grid,
  Theme,
  createStyles,
  withStyles,
} from '@material-ui/core';

import Header from './sub-components/Header';

const styles = (theme: Theme) =>
  createStyles({
    root: {
      flexGrow: 1,
    },
    drawerHeader: {
      display: 'flex',
      alignItems: 'center',
      padding: '0 8px',
      ...theme.mixins.toolbar,
      justifyContent: 'flex-start',
    },
    main: {
      marginTop: 10,
    },
    card: {
      marginBottom: 10,
    },
    container: {
      marginLeft: '5%',
      marginRight: '5%',
    },
  });

const ListManagementPage: React.FC<any> = props => {
  const [list, setList] = useState([1, 3, 5, 7, 9]);
  const { classes } = props;

  return (
    <div className={classes.root}>
      <Header />
      <div className={classes.container}>
        <h1>{props.location.pathname.slice(7)}</h1>
        <Grid container spacing={16} className={classes.main}>
          <Grid item xs={12}>
            {list.map((item, idx) => (
              <Card key={idx} className={classes.card}>
                <CardContent>{item}</CardContent>
              </Card>
            ))}
          </Grid>
        </Grid>
      </div>
    </div>
  );
};

export default withStyles(styles)(withRouter(ListManagementPage));
